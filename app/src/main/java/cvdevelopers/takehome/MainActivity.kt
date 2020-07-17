package cvdevelopers.takehome

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cvdevelopers.githubstalker.R
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import android.view.MenuItem
import cvdevelopers.takehome.models.Client
import cvdevelopers.githubstalker.ui.fragments.UserListFragment
import cvdevelopers.githubstalker.ui.mvp.presenter.MainActivityPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as LuminaryTakeHomeApplication).appComponent.inject(this)

    }

}
