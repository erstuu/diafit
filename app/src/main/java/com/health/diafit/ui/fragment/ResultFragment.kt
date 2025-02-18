package com.health.diafit.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.diafit.R
import com.health.diafit.adapter.WebLinkAdapter
import com.health.diafit.databinding.FragmentResultBinding
import com.health.diafit.data.remote.request.UserDataPredict
import com.health.diafit.data.remote.response.WebLink
import com.health.diafit.databinding.FragmentResultDiaBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultDiaBinding? = null
    private val binding get() = _binding!!

    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultDiaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userData = args.userDataPredict

        setupToolbar()
        setupShapeDoubleCheckCard()
        setupView(userData)
        setupRecyclerView()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivHome.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToHomeFragment2()
            findNavController().navigate(action)
        }
    }

    private fun setupShape(cardView: MaterialCardView, cornerSizeResId: Int, colorResId: Int) {
        val cornerSize = resources.getDimension(cornerSizeResId)
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setBottomLeftCorner(RoundedCornerTreatment())
            .setBottomLeftCornerSize(cornerSize)
            .setBottomRightCorner(RoundedCornerTreatment())
            .setBottomRightCornerSize(cornerSize)
            .build()

        cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), colorResId))
        cardView.shapeAppearanceModel = shapeAppearanceModel
        cardView.strokeWidth = 0
    }

    private fun setupShapeDoubleCheckCard() {
        setupShape(binding.resultCard, R.dimen.corner_radius_grey_background, R.color.grey)
    }

    private fun setupView(userData: UserDataPredict) {
        binding.tvResultGender.text = userData.gender
        binding.tvResultAge.text = userData.age.toString()
        binding.tvResultSmokingHistory.text = userData.smokingHistory
        binding.tvResultHeartDisease.text = userData.heartDisease
        binding.tvResultHypertension.text = userData.hypertension
        binding.tvResultBodyMassIndex.text = userData.bodyMassIndex.toString()
        binding.tvResultHemoglobinA1cLevel.text = userData.hemoglobinLevel.toString()
        binding.tvResultBloodGlucoseLevel.text = userData.glucoseLevel.toString()
        binding.tvDiabeteResult.text = userData.description
    }

    private fun setupRecyclerView() {
        val listNews = listOf(
            WebLink("10 Cara Mengontrol Kadar Gula Darah bagi Orang Diabetes",
                "https://hellosehat.com/diabetes/cara-mengontrol-gula-darah/",
                "https://cdn.hellosehat.com/wp-content/uploads/2017/09/shutterstock_1727756401.jpg?w=750&q=75",
                "Diabetes belum bisa disembuhkan sepenuhnya, tapi penyandang diabetes tetap bisa beraktivitas normal dengan mengontrol kadar gula darah dalam batas normal. Simak beberapa tips menjaga kadar gula darah tetap normal berikut ini."),

            WebLink("18 Makanan Penurun Gula Darah untuk Mengatasi Diabetes",
                "https://health.kompas.com/read/2020/08/16/073100568/18-makanan-penurun-gula-darah-untuk-mengatasi-diabetes?page=all",
                "https://asset.kompas.com/crops/Ilc3cMjtFGCJhfszuUy7HSRVwwI=/0x0:998x665/1200x800/data/photo/2020/02/24/5e53cde894df3.jpg", "Essential lifestyle tips and changes you can adopt to help prevent diabetes and improve your overall health."),

            WebLink("14 Cara Menurunkan Gula Darah yang Paling Ampuh",
                "https://www.alodokter.com/cek-cara-menurunkan-gula-darah-di-sini",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1613445255/attached_image/cek-cara-menurunkan-gula-darah-di-sini-0-alodokter.jpg",
                "Penderita diabetes perlu memahami cara menurunkan gula darah agar kadarnya tetap stabil. Tidak hanya efektif mencegah lonjakan kadar gula, berbagai cara menurunkan gula darah ini juga dapat meminimalkan risiko terjadinya komplikasi akibat diabetes."),
        )

        binding.rvSuggestion.layoutManager = LinearLayoutManager(requireContext())
        val adapter = WebLinkAdapter { webLink ->
            val linkWeb = WebLink(webLink.title, webLink.url, webLink.imageUrl, webLink.description)
            val action = ResultFragmentDirections.actionResultFragmentToNewsFragment(linkWeb)
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.rvSuggestion.adapter = adapter
        adapter.submitList(listNews)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}