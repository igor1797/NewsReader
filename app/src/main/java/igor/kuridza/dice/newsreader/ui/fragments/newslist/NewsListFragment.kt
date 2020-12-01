package igor.kuridza.dice.newsreader.ui.fragments.newslist

import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import igor.kuridza.dice.newsreader.R
import igor.kuridza.dice.newsreader.common.*
import igor.kuridza.dice.newsreader.databinding.FragmentNewsListBinding
import igor.kuridza.dice.newsreader.ui.adapters.NewsAdapter
import igor.kuridza.dice.newsreader.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalPagingApi
class NewsListFragment : BaseFragment<FragmentNewsListBinding>(), NewsAdapter.SingleNewsClickListener {

    private val newsListViewModel: NewsListViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback
    private lateinit var loadStateListener: (CombinedLoadStates) -> Unit

    override fun getLayoutResourceId(): Int = R.layout.fragment_news_list

    override fun setUpUi() {
        setUpBindingData()
        observeNewsList()
        initBottomSheetCallback()
        initStateListener()
        setBehaviour()
        setOnMenuItemClickListener()
        addLoadStateListener()
        setBottomSheetItemsOnCLickListener()
        observeBottomSheetState()
        observeRadioButtonOption()
    }

    private fun initStateListener(){
        loadStateListener = { loadState: CombinedLoadStates ->
            viewBinding.progressBar.isVisible = loadState.refresh is LoadState.Loading

            if(loadState.refresh is LoadState.Error)
                displayDialogErrorAlert()
        }
    }

    private fun addLoadStateListener(){
        newsAdapter.addLoadStateListener(loadStateListener)
    }

    private fun observeBottomSheetState(){
        newsListViewModel.bottomSheetState.observe(this){ newState ->
            newState?.let {
                bottomSheetBehavior.state = newState
                setMenuItemAndBackgroundVisibilityByNewBottomSheetState(newState)
            }
        }
    }

    private fun initBottomSheetCallback(){
        bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                newsListViewModel.setBottomSheetState(newState)
                setMenuItemAndBackgroundVisibilityByNewBottomSheetState(newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                viewBinding.background.alpha = slideOffset - 0.2F
            }
        }
    }

    private fun setMenuItemAndBackgroundVisibilityByNewBottomSheetState(newState: Int){
        val menuItemPickSource =  viewBinding.toolbar.menu.findItem(R.id.pickSource)
        menuItemPickSource.isVisible =
            when(newState){
                BottomSheetBehavior.STATE_HIDDEN -> {
                    setBackgroundVisibilityGone()
                    true
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    setBackgroundVisibilityGone()
                    true
                }
                else -> {
                    setBackgroundVisibilityVisible()
                    false
                }
            }
    }

    private fun setBackgroundVisibilityGone(){
        viewBinding.background.gone()
    }

    private fun setBackgroundVisibilityVisible(){
        viewBinding.background.visible()
    }

    private fun setBehaviour(){
        bottomSheetBehavior = BottomSheetBehavior.from(viewBinding.chooseSourceBottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }

    private fun setBottomSheetItemsOnCLickListener() {
        viewBinding.header.setOnClickListener {
            hideBottomSheet()
        }

        viewBinding.confirmSource.setOnClickListener {
            val checkedOption = viewBinding.radioPickSourceGroup.checkedRadioButtonId
            val newSource = when (checkedOption) {
                R.id.bbcNewsOption -> BBC_NEWS_SOURCE
                R.id.theNextWebOption -> THE_NEXT_WEB_SOURCE
                R.id.bbcSportOption -> BBC_SPORT_SOURCE
                R.id.bleacherReportOption -> BLEACHER_REPORT_SOURCE
                else -> BUSINESS_INSIDER_SOURCE
            }
            newsListViewModel.apply {
                setSource(newSource)
                setRadioButtonOption(checkedOption)
            }
            hideBottomSheet()
        }

        viewBinding.background.setOnClickListener {
            hideBottomSheet()
        }
    }

    private fun hideBottomSheet(){
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun expandBottomSheet(){
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setUpBindingData(){
        viewBinding.apply {
            adapter = newsAdapter
            lifecycleOwner = this@NewsListFragment
        }
    }

    private fun observeNewsList() {
        newsListViewModel.newsList.observe(this, {
            it?.let {
                newsAdapter.submitData(lifecycle, it)
            }
        })
    }

    private fun observeRadioButtonOption(){
        newsListViewModel.selectedRadioButtonOption.observe(this){
            it?.let { selectedOption ->
                viewBinding.radioPickSourceGroup.check(selectedOption)
            }
        }
    }

    private fun displayDialogErrorAlert(){
        val dialog = MaterialAlertDialogBuilder(this.requireContext(), R.style.AlertDialogTheme)
            .setTitle(R.string.errorDialogTitle)
            .setMessage(R.string.errorDialogMessage)
            .setPositiveButton(R.string.errorPositiveButtonText){ dialog, _ ->
                dialog?.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun setOnMenuItemClickListener(){
        viewBinding.toolbar.setOnMenuItemClickListener { menuItem ->
            if(menuItem.itemId == R.id.pickSource){
                expandBottomSheet()
            }
            true
        }
    }

    override fun onSingleNewsClicked(titleOfSingleNewsInList: String) {
        val action = NewsListFragmentDirections.goToSingleNewsDetailsViewPagerFragment(
            titleOfSingleNewsInList
        )
        findNavController().navigate(action)
    }

    private fun removeLoadStateListener(){
        newsAdapter.removeLoadStateListener(loadStateListener)
    }

    private fun removeBottomSheetCallback(){
        bottomSheetBehavior.removeBottomSheetCallback(bottomSheetCallback)
    }

    override fun onDestroyView() {
        removeBottomSheetCallback()
        removeLoadStateListener()
        super.onDestroyView()
    }
}