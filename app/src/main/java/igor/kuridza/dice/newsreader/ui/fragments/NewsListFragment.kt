package igor.kuridza.dice.newsreader.ui.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.databinding.FragmentNewsListBinding
import igor.kuridza.dice.newsreader.ui.adapters.NewsAdapter
import igor.kuridza.dice.newsreader.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NewsListFragment : BaseFragment(), NewsAdapter.SingleNewsClickListener {

    private val newsListViewModel: NewsListViewModel by sharedViewModel()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }

    override fun getLayoutResourceId(): Int = R.layout.fragment_news_list

    override fun setUpUi() {
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

    private fun observeNewsList(){
        newsListViewModel.newsList.observe(viewLifecycleOwner){
            newsAdapter.setNews(it)
        }
    }

    override fun onSingleNewsClicked(positionOfSingleNewsInList: Int) {
        val action = NewsListFragmentDirections.goToSingleNewsDetailsViewPagerFragment(positionOfSingleNewsInList)
        findNavController().navigate(action)
    }
}