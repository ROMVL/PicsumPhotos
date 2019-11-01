package ua.romanik.vladislav.picsumphotos.presentation.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ua.romanik.vladislav.picsumphotos.R
import ua.romanik.vladislav.picsumphotos.domain.model.error.ErrorModel
import ua.romanik.vladislav.picsumphotos.presentation.base.viewmodel.BaseViewModel

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int
    abstract val viewModel: BaseViewModel

    abstract fun handleError()

    protected lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            error.observe(this@BaseFragment.viewLifecycleOwner, Observer<ErrorModel> { handleError() })
        }
    }

    open fun initUI() {
        with(binding) {
            lifecycleOwner = this@BaseFragment.viewLifecycleOwner
        }
    }
}