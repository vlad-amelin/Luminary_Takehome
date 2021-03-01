package cvdevelopers.takehome

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import butterknife.BindView
import butterknife.ButterKnife
import cvdevelopers.githubstalker.R
import cvdevelopers.takehome.adapter.RecyclerViewAdapter
import cvdevelopers.takehome.api.RandomUserApiEndpoint
import cvdevelopers.takehome.models.ApiResponse
import cvdevelopers.takehome.utils.image.ImageCache
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @BindView(R.id.user_list)
    lateinit var userList: RecyclerView

    @BindView(R.id.refreshView)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    companion object {
        var imageCache = ImageCache()
    }

    @Inject
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    @Inject
    lateinit var apiInterface: RandomUserApiEndpoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        (application as LuminaryTakeHomeApplication).appComponent.inject(this)

        userList.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerViewAdapter = RecyclerViewAdapter()
        userList.adapter = recyclerViewAdapter

        // SetOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            imageCache.clear()
            if (!configOrientation()) {
                apiCallBack()
            }else {
                recyclerViewAdapter.clear()
            }
        }

        if (imageCache.size() == 0 && !configOrientation()) {
            apiCallBack()
        } else {
            recyclerViewAdapter.convertMaptoArrayListObject(imageCache)
        }
    }

    private fun apiCallBack() {
        apiInterface.getClient("1").observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : SingleObserver<ApiResponse> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onSuccess(items: ApiResponse) {
                        recyclerViewAdapter.setCacheValue(items.results, imageCache)
                        recyclerViewAdapter.convertMaptoArrayListObject(imageCache)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("", "onError" + e.message)
                    }
                })
    }

    private fun configOrientation(): Boolean {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("Orientation", "true")
            return true
        } else {
            Log.d("Orientation", "false")
            return false
        }
    }
}