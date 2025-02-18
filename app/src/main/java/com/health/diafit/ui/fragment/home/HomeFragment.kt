package com.health.diafit.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.diafit.R
import com.health.diafit.adapter.WebLinkAdapter
import com.health.diafit.databinding.FragmentHomeBinding
import com.health.diafit.data.remote.response.WebLink
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.name.isEmpty()) {
                greeting(getString(R.string.guest))
            } else {
                greeting(user.name)
            }
        }

        setupShapeBackground()
        setupAction()
        setupRecyclerView()
    }

    private fun setupAction() {
        binding.btnCekDiabetes.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment2_to_inputDataFragment)
        )
    }

    private fun setupRecyclerView() {
        val listNews = listOf(
            WebLink("14 Cara Menurunkan Gula Darah yang Paling Ampuh",
                "https://www.alodokter.com/cek-cara-menurunkan-gula-darah-di-sini",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1613445255/attached_image/cek-cara-menurunkan-gula-darah-di-sini-0-alodokter.jpg",
                "Penderita diabetes perlu memahami cara menurunkan gula darah agar kadarnya tetap stabil. Tidak hanya efektif mencegah lonjakan kadar gula, berbagai cara menurunkan gula darah ini juga dapat meminimalkan risiko terjadinya komplikasi akibat diabetes."),

            WebLink("Diabetes - Gejala, Penyebab, dan Pencegahan",
                "https://www.biofarma.co.id/id/announcement/detail/diabetes-gejala-penyebab-dan-pencegahan",
                "https://www.biofarma.co.id/media/image/originals/post/2023/11/24/1.png",
                "Diabetes merupakan penyakit metabolik kronis yang ditandai dengan peningkatan kadar glukosa darah (atau gula darah) yang seiring waktu menyebabkan kerusakan serius pada jantung, pembuluh darah, mata, ginjal, dan saraf."),

            WebLink("10 Cara Mengontrol Kadar Gula Darah bagi Orang Diabetes",
                "https://hellosehat.com/diabetes/cara-mengontrol-gula-darah/",
                "https://cdn.hellosehat.com/wp-content/uploads/2017/09/shutterstock_1727756401.jpg?w=750&q=75",
                "Diabetes belum bisa disembuhkan sepenuhnya, tapi penyandang diabetes tetap bisa beraktivitas normal dengan mengontrol kadar gula darah dalam batas normal. Simak beberapa tips menjaga kadar gula darah tetap normal berikut ini."),

            WebLink("18 Makanan Penurun Gula Darah untuk Mengatasi Diabetes",
                "https://health.kompas.com/read/2020/08/16/073100568/18-makanan-penurun-gula-darah-untuk-mengatasi-diabetes?page=all",
                "https://asset.kompas.com/crops/Ilc3cMjtFGCJhfszuUy7HSRVwwI=/0x0:998x665/1200x800/data/photo/2020/02/24/5e53cde894df3.jpg", "Essential lifestyle tips and changes you can adopt to help prevent diabetes and improve your overall health."),

            WebLink("Simak Tips Atasi Diabetes Gestasional saat Hamil",
                "https://herminahospitals.com/id/articles/simak-tips-atasi-diabetes-gestasional-saat-hamil.amp",
                "https://dk4fkkwa4o9l0.cloudfront.net/production/uploads/article/image/697/Untitled_2.png", "Follow this step-by-step guide to learn how to prevent type 2 diabetes and reduce your risk.")
        )

        binding.rvArtikel.layoutManager = LinearLayoutManager(requireContext())
        val adapter = WebLinkAdapter { webLink ->
            val linkWeb = WebLink(webLink.title, webLink.url, webLink.imageUrl, webLink.description)
            val action = HomeFragmentDirections.actionHomeFragment2ToNewsFragment(linkWeb)
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.rvArtikel.adapter = adapter
        adapter.submitList(listNews)
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

    private fun greeting(name: String) {
        binding.userName.text = name
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}