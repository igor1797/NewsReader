package igor.kuridza.dice.newsreader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.databinding.FragmentNewsListBinding
import igor.kuridza.dice.newsreader.ui.adapters.NewsAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class NewsListFragment : Fragment() {

    private val newsListViewModel: NewsListViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBindingData(view)
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
}