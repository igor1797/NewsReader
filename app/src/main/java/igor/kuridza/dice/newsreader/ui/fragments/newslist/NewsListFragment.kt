package igor.kuridza.dice.newsreader.ui.fragments.newslist

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.common.gone
import igor.kuridza.dice.newsreader.common.visible
import igor.kuridza.dice.newsreader.databinding.FragmentNewsListBinding
import igor.kuridza.dice.newsreader.ui.adapters.NewsAdapter
import igor.kuridza.dice.newsreader.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_dialog_error.*
import kotlinx.android.synthetic.main.fragment_news_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsListFragment : BaseFragment<FragmentNewsListBinding>(), NewsAdapter.SingleNewsClickListener {

    private val newsListViewModel: NewsListViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }

    override fun getLayoutResourceId(): Int = R.layout.fragment_news_list

    override fun setUpUi() {
        setUpBindingData(this.requireView())
        observeNewsList()
        addLoadStateListenerToAdapter()
    }

    private fun setUpBindingData(view: View){
        viewBinding.apply {
            adapter = newsAdapter
            lifecycleOwner = this@NewsListFragment
        }
    }

    private fun addLoadStateListenerToAdapter(){
        newsAdapter.addLoadStateListener { loadState->
            if(loadState.refresh is LoadState.Loading){
                progressBar.visible()
            }else{
                progressBar.gone()
            }
        }
    }

    private fun observeNewsList() {
        newsListViewModel.newsList.observe(this, {
            it?.let {
                newsAdapter.submitData(lifecycle, it)
            }
        })
    }

    private fun displayDialogErrorAlert(){
        val dialogInflater = LayoutInflater.from(this.requireContext())
        val dialogView = dialogInflater.inflate(R.layout.fragment_dialog_error, dialogContent, false)
        val dialog = AlertDialog.Builder(this.requireContext(), R.style.CustomErrorAlertDialog)
            .setView(dialogView)
            .create()
        dialog.show()
        dialogView.findViewById<TextView>(R.id.buttonOk).setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onSingleNewsClicked(titleOfSingleNewsInList: String) {
        val action = NewsListFragmentDirections.goToSingleNewsDetailsViewPagerFragment(
            titleOfSingleNewsInList
        )
        findNavController().navigate(action)
    }
}