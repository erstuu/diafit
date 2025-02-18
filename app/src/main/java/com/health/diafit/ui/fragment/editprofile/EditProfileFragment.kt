package com.health.diafit.ui.fragment.editprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.health.diafit.R
import com.health.diafit.data.ResultState
import com.health.diafit.databinding.FragmentEditProfileBinding
import com.health.diafit.data.remote.request.UserInputProfile
import com.health.diafit.util.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()
    private val args: EditProfileFragmentArgs by navArgs()
    private val progressDialog by lazy { ProgressDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        val name = args.usernameAndToken
        updateUsernameUI(name)
        setupAction()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupAction() {
        binding.btnSave.setOnClickListener {
            val username = binding.tiUsername.text.toString()

            if (validateUserData(username)) {
                viewModel.setUsername(username)
                viewModel.usernameState.observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            showSnackbar(getString(R.string.profile_updated), R.id.bottom_navigation, R.color.diaPrimary)
                        }
                        is ResultState.Error -> {
                            progressDialog.hideLoading()
                            showSnackbar(result.error, R.id.bottom_navigation, R.color.red)
                        }
                    }
                }
            }
        }
    }

    private fun showSnackbar(errorMessage: String, anchorViewId: Int? = null, color: Int = R.color.diaPrimary) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), color))
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setAction("OK") {
                dismiss()
            }
            anchorView = if (anchorViewId != null) requireActivity().findViewById(anchorViewId) else null
        }.show()
    }

    private fun validateUserData(username: String): Boolean {
        return if (username.isEmpty()) {
            showSnackbar(getString(R.string.nama_tidak_boleh_kosong))
            false
        } else {
            true
        }
    }

    private fun updateUsernameUI(username: String) {
        binding.tiUsername.setText(username)
    }

}