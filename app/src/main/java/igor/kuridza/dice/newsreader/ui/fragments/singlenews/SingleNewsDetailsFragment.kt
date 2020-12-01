package igor.kuridza.dice.newsreader.ui.fragments.singlenews

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.databinding.FragmentSingleNewsDetailsBinding
import igor.kuridza.dice.newsreader.ui.adapters.ViewPagerAdapter
import igor.kuridza.dice.newsreader.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SingleNewsDetailsFragment : BaseFragment<FragmentSingleNewsDetailsBinding>(){

    private val viewPagerAdapter by lazy { ViewPagerAdapter() }
    private val singleNewsDetailsViewModel: SingleNewsDetailsViewModel by viewModel()
    private val args: SingleNewsDetailsFragmentArgs by navArgs()
    private lateinit var titleOfSingleNews: String
    private lateinit var singleNewsPageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun getLayoutResourceId(): Int = R.layout.fragment_single_news_details

    override fun setUpUi() {
        titleOfSingleNews = args.titleOfSingleNewsInList
        setViewBinding()
        setOnPageChangeCallback()
        setupViewPager()
        setupToolbar()
        observeNewsList()
        showCurrentSelectedSingleNews()
    }

    private fun setViewBinding(){
        viewBinding.apply {
            viewModel = singleNewsDetailsViewModel
            lifecycleOwner = this@SingleNewsDetailsFragment
        }
    }

    private fun setViewPagerCurrentItem(position: Int){
        viewBinding.singleNewsDetailsViewPager.post {
            viewBinding.singleNewsDetailsViewPager.setCurrentItem(position, true)
        }
    }

    private fun setOnPageChangeCallback(){
        singleNewsPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val singleNews = viewPagerAdapter.getSingleNewsByPosition(position)
                singleNewsDetailsViewModel.apply {
                    setSelectedSingleNews(singleNews)
                    setPositionOfSelectedSingleNews(position)
                }
            }
        }
    }

    private fun observeNewsList(){
        singleNewsDetailsViewModel.newsList.observe(this){ newsList ->
           newsList?.let {
               viewPagerAdapter.setNewsList(it)
               setViewPagerCurrentItem(viewPagerAdapter.getSingleNewsPositionByTitle(titleOfSingleNews))
           }
        }
    }

    private fun showCurrentSelectedSingleNews(){
        singleNewsDetailsViewModel.positionOfSelectedSingleNews.value?.let {
            setViewPagerCurrentItem(it)
        }
    }

    private fun setupToolbar() {
        viewBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupViewPager(){
        viewBinding.singleNewsDetailsViewPager.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(singleNewsPageChangeCallback)
        }
    }

    private fun unRegisterOnPageChangeCallbackFromViewPager(){
        viewBinding.singleNewsDetailsViewPager.unregisterOnPageChangeCallback(singleNewsPageChangeCallback)
    }

    override fun onDestroyView() {
        unRegisterOnPageChangeCallbackFromViewPager()
        super.onDestroyView()
    }
}