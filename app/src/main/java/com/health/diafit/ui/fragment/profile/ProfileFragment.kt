package com.health.diafit.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.diafit.R
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.health.diafit.data.remote.response.UserSession
import com.health.diafit.databinding.FragmentProfileBinding
import com.health.diafit.databinding.FragmentProfileDiafitBinding
import com.health.diafit.ui.activity.main.MainActivity
import com.health.diafit.util.BottomChoseLanguageDialog
import com.health.diafit.util.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileDiafitBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private val progressDialog by lazy { ProgressDialog(requireContext()) }
    private val bottomChoseLanguageDialog by lazy { BottomChoseLanguageDialog(requireContext(), viewModel) }
    private lateinit var userSession: UserSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileDiafitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupShapeBackground()

        viewModel.getUsername().observe(viewLifecycleOwner) { name ->
            if (name.isEmpty()) {
                updateUsernameUI(getString(R.string.guest))
            } else {
                updateUsernameUI(name)
            }

            setupAction(name)
        }

        setupToolbar()
    }

    private fun setupShapeBackground() {
        val cornerSizeBackground = resources.getDimension(R.dimen.corner_radius_grey_background)
        val shapeAppearanceModelBackground = ShapeAppearanceModel.builder()
            .setBottomLeftCorner(RoundedCornerTreatment())
            .setBottomLeftCornerSize(cornerSizeBackground)
            .setBottomRightCorner(RoundedCornerTreatment())
            .setBottomRightCornerSize(cornerSizeBackground)
            .build()

        val shapeDrawableBackground = MaterialShapeDrawable(shapeAppearanceModelBackground)
        shapeDrawableBackground.fillColor = ContextCompat.getColorStateList(requireContext(), R.color.diaPrimary)
        binding.materialCardView.background = shapeDrawableBackground
    }

    private fun updateUsernameUI(name: String) {
        binding.userName.text = name
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
    }

    private fun setupAction(name: String) {
        binding.cvLanguage.setOnClickListener {
            bottomChoseLanguageDialog.showDialog()
        }

        binding.cvEditInformation.setOnClickListener{
            val navigateToEditProfile = ProfileFragmentDirections
                .actionProfileFragmentToEditProfileFragment(name)
            findNavController().navigate(navigateToEditProfile)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}