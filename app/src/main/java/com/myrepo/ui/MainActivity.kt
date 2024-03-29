package com.myrepo.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myrepo.R
import com.myrepo.base.BaseActivity
import com.myrepo.databinding.LayoutNoDataBinding
import com.myrepo.databinding.MainActivityBinding
import com.myrepo.db.DBHelper
import com.myrepo.db.RepoData
import com.myrepo.model.Resource
import com.myrepo.ui.adapter.AdapterTrending
import com.myrepo.utill.hideKeyboard
import com.myrepo.utill.showSnackBar

class MainActivity : BaseActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var bindingNoData: LayoutNoDataBinding
    private lateinit var viewModel: MainViewModel
    private var adapterTrending: AdapterTrending? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        bindingNoData = LayoutNoDataBinding.bind(binding.root)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        init()
        setOnClickListener()
        observerHandler()
//        viewModel.getRepoLists("Q",this)
        checkInternetConnectivity()
    }

    private fun setOnClickListener() {
        bindingNoData.btnRetry.setOnClickListener {
            binding.includeNoData.visibility = View.GONE
            if (checkInternetConnectivity()) {
                viewModel.getRepoLists("Q", this)
            } else {
                dataFromDB()
            }
        }

        binding.imgSearch.setOnClickListener {
            searchVisibility(true)
        }
        binding.imgBack.setOnClickListener {
            searchVisibility(false)
//            onBackPressedDispatcher.onBackPressed()
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length?.rem(3) == 0) {
                    viewModel.getRepoLists(s.toString(), this@MainActivity)
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                v?.rootView?.let { hideKeyboard(it) }
                true
            } else {
                false
            }
        }
    }

    private fun init() {
        binding.rvTrendingList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        binding.refreshLayout.setOnRefreshListener {
            binding.rvTrendingList.visibility = View.GONE
            binding.refreshLayout.isRefreshing = false
//            if (checkInternetConnectivity())
            viewModel.getRepoLists("Q", this)
        }
    }

    private fun observerHandler() {
        viewModel.apply {
            this.repoLiveData.observe(this@MainActivity) {
                when (it) {
                    is Resource.Loading -> {
                        shimmerAction(true)
                    }

                    is Resource.Success -> {
                        it.let {
                            it.data?.let { it1 -> setRepoListAdapter(it1) }
                        }
                    }

                    is Resource.Error -> {
                        emptyDataLayoutHandler(
                            true,
                            "Something went wrong...",
                            "An alien is probably blocking your signal."
                        )
                    }

                    else -> {
                        emptyDataLayoutHandler(
                            true,
                            "Something went wrong...",
                            "An alien is probably blocking your signal."
                        )
                    }
                }
            }
        }
    }

    private fun emptyDataLayoutHandler(
        isVisible: Boolean,
        title: String? = null,
        subTitle: String? = null
    ) {
        binding.includeNoData.visibility = if (isVisible) View.VISIBLE else View.GONE
        if (isVisible) {
            if (!checkInternetConnectivity()) {
                bindingNoData.txtNoData.text = getString(R.string.no_internet)
            } else {
                bindingNoData.txtNoData.text = title
            }
            bindingNoData.txtNoDataSubTitle.visibility =
                if (subTitle != null) View.VISIBLE else View.GONE
            bindingNoData.btnRetry.visibility = if (subTitle != null) View.VISIBLE else View.GONE
            bindingNoData.txtNoDataSubTitle.text = subTitle
        }
        shimmerAction(false)
    }

    private fun shimmerAction(isStart: Boolean?) {
        if (isStart == true) {
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.refreshLayout.visibility = View.GONE
            binding.shimmerLayout.startShimmer()
        } else {
            binding.shimmerLayout.apply {
                stopShimmer()
                visibility = View.GONE
            }
            binding.refreshLayout.visibility = View.VISIBLE
        }
    }

    private fun setRepoListAdapter(list: ArrayList<RepoData>) {
        if (list.size > 0) {
            binding.rvTrendingList.visibility = View.VISIBLE
            binding.refreshLayout.visibility = View.VISIBLE
        } else {
            binding.rvTrendingList.visibility = View.GONE
            binding.refreshLayout.visibility = View.GONE
        }
        emptyDataLayoutHandler(!binding.rvTrendingList.isVisible, "No Data Available")
        adapterTrending = AdapterTrending(list)
        binding.rvTrendingList.adapter = adapterTrending
    }

    private fun searchVisibility(isSearchVisible: Boolean) {
        if (isSearchVisible) {
            binding.edtSearch.visibility = View.VISIBLE
            binding.imgBack.visibility = View.VISIBLE
            binding.txtTitle.visibility = View.GONE
            binding.imgSearch.visibility = View.GONE
        } else {
            binding.edtSearch.visibility = View.GONE
            binding.imgBack.visibility = View.GONE
            binding.txtTitle.visibility = View.VISIBLE
            binding.imgSearch.visibility = View.VISIBLE
        }

    }

    private fun checkInternetConnectivity(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network: Network?
        val networkCapability: NetworkCapabilities?

        val isConnected: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            network = connectivityManager.activeNetwork
            networkCapability = connectivityManager.getNetworkCapabilities(network)

            networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
        }


        if (!isConnected) {
            showSnackBar(binding.includeHeader)
            val db = DBHelper(this, null)
            val list = db.getAllRepoTableData()
            setRepoListAdapter(list)
        } else {
            viewModel.getRepoLists("Q", this)
        }

        return isConnected
    }

    private fun dataFromDB() {
        val db = DBHelper(this, null)
        val list = db.getAllRepoTableData()
        setRepoListAdapter(list)
    }

}