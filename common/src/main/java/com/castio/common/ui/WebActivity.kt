package com.castio.common.ui

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.castio.common.R
import com.castio.common.base.BaseActivity
import com.castio.common.base.BaseViewModel
import com.castio.common.databinding.ActivityCommonWebviewBinding
import com.castio.common.utils.ToastUtils
import com.castio.common.widget.MultiStateView
import com.just.agentweb.AgentWeb
import com.just.agentweb.BaseIndicatorView
import com.just.agentweb.WebChromeClient
import com.youth.banner.indicator.BaseIndicator
import kotlinx.android.synthetic.main.activity_common_webview.*
import java.lang.ref.WeakReference


class WebActivity : BaseActivity<ActivityCommonWebviewBinding, BaseViewModel>(),
    View.OnClickListener {
    private lateinit var mAgentWeb: AgentWeb
    override fun initViewModel(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun initViewModelId(): Int? {
        return null
    }

    override fun getLayout(): Int {
        return R.layout.activity_common_webview
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        val url = intent.getStringExtra("url")
        if (TextUtils.isEmpty(url)) {
            viewModel.state.postValue(MultiStateView.ViewState.ERROR)
            return
        }
        dataBinding.onClickListener = this
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(webview, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(ContextCompat.getColor(this, R.color.green_50), 2)
            .setWebChromeClient(webChromeClient)
            .createAgentWeb()
            .ready()
            .go(url)

        val webSetting = mAgentWeb.webCreator?.webView?.settings
        webSetting?.javaScriptEnabled = true
        mAgentWeb.jsInterfaceHolder?.addJavaObject("android", AndroidInterface(this))
        //调用js方法
//        mAgentWeb.jsAccessEntrace.quickCallJs("quickCallJs", "params", "params2")
    }


    private val webChromeClient = object : WebChromeClient() {

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            dataBinding.title = title
        }

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress);
        }

    }

    override fun initData() {
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }


    class AndroidInterface(webActivity: WebActivity) : Any() {
        private val webActivity: WeakReference<WebActivity> = WeakReference(webActivity)

        @JavascriptInterface
        fun jsToastMethod(toast: String?) {
            webActivity.get()?.let {
                ToastUtils.showToast(webActivity.get(), toast)
            }

        }


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
            else -> {
            }
        }
    }

}