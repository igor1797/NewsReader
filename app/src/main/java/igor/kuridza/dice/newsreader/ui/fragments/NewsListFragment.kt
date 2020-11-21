package igor.kuridza.dice.newsreader.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.common.gone
import igor.kuridza.dice.newsreader.common.visible
import igor.kuridza.dice.newsreader.databinding.FragmentNewsListBinding
import igor.kuridza.dice.newsreader.model.Resource
import igor.kuridza.dice.newsreader.ui.adapters.NewsAdapter
import igor.kuridza.dice.newsreader.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_dialog_error.*
import kotlinx.android.synthetic.main.fragment_news_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NewsListFragment : BaseFragment(), NewsAdapter.SingleNewsClickListener {

    private val newsListViewModel: NewsListViewModel by sharedViewModel()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }

    override fun getLayoutResourceId(): Int = R.layout.fragment_news_list

    override fun setUpUi(savedInstanceState: Bundle?) {
        setUpBindingData(this.requireView())
        observeNewsList()
    }

    private fun setUpBindingData(view: View){
        val binding = FragmentNewsListBinding.bind(view)
        binding.apply {
            adapter = newsAdapter
            lifecycleOwner = this@NewsListFragment
        }
    }

    private fun observeNewsList() {
        newsListViewModel.newsList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    progressBar.visible()
                }
                is Resource.Success -> {
                    val news = response.data
                    if(news != null) {
                        newsAdapter.setNews(response.data)
                        progressBar.gone()
                    }
                }
                is Resource.Error -> {
                    displayDialogErrorAlert()
                    progressBar.gone()
                }
            }
        }
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

    override fun onSingleNewsClicked(positionOfSingleNewsInList: Int) {
        val action = NewsListFragmentDirections.goToSingleNewsDetailsViewPagerFragment(positionOfSingleNewsInList)
        findNavController().navigate(action)
    }
}