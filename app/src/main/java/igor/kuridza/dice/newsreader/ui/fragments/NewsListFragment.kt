package igor.kuridza.dice.newsreader.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.ui.adapters.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news_list.*
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
        setUpRecycler()
        observeNewsList()
    }

    private fun setUpRecycler(){
        newsRecycler.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@NewsListFragment.context)
        }
    }

    private fun observeNewsList(){
        newsListViewModel.newsList.observe(viewLifecycleOwner){
            newsAdapter.setNews(it)
        }
    }
}