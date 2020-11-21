package igor.kuridza.dice.newsreader.ui.fragments

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.common.VIEW_PAGER_CURRENT_ITEM_POSITION
import igor.kuridza.dice.newsreader.ui.adapters.ViewPagerAdapter
import igor.kuridza.dice.newsreader.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_single_news_details.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SingleNewsDetailsFragment : BaseFragment(){

    private val viewPagerAdapter by lazy { ViewPagerAdapter() }
    private val newsListViewModel: NewsListViewModel by sharedViewModel()
    private val args: SingleNewsDetailsFragmentArgs by navArgs()
    private var positionOfSingleNews: Int = 0

    override fun getLayoutResourceId(): Int = R.layout.fragment_single_news_details

    override fun setUpUi(savedInstanceState: Bundle?) {
        observeNewsList()
        setupToolbar()
        positionOfSingleNews = args.positionOfSingleNewsInList
        setupViewPager()
        if(savedInstanceState!=null){
            singleNewsDetailsViewPager?.currentItem = savedInstanceState.getInt(
                VIEW_PAGER_CURRENT_ITEM_POSITION)
        }
    }

    private fun observeNewsList(){
        newsListViewModel.newsList.observe(viewLifecycleOwner){
            val news = it.data
            if(news != null)
                viewPagerAdapter.setNews(it.data)
        }
    }

    private fun setupToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back_icon)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.goToNewsListFragment)
        }
    }

    private fun setupViewPager(){
        val singleNewsPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                toolbar.title = viewPagerAdapter.getSingleNewsTitleByPosition(position)
            }
        }

        singleNewsDetailsViewPager.apply {
            adapter = viewPagerAdapter
            setCurrentItem(positionOfSingleNews, false)
            registerOnPageChangeCallback(singleNewsPageChangeCallback)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(VIEW_PAGER_CURRENT_ITEM_POSITION, singleNewsDetailsViewPager?.currentItem ?: 0)
    }
}