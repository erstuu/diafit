package com.health.diafit.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.diafit.R
import com.health.diafit.ui.activity.main.MainActivity
import com.health.diafit.ui.fragment.profile.ProfileViewModel
import java.util.Locale

class BottomChoseLanguageDialog(
    private val context: Context,
    private val viewModel: ProfileViewModel
) {
    private val bottomChangeLanguageDialog: BottomSheetDialog = BottomSheetDialog(context)
    private val colorStateList = ContextCompat.getColorStateList(context, R.color.radio_button_selector)

    init {
        val dialogLayout = LayoutInflater.from(context).inflate(
            R.layout.bottom_language_picker_dialog,
            null as ViewGroup?, false
        )

        val card = dialogLayout.findViewById<MaterialCardView>(R.id.card_chose_language)
        setupShape(card, R.dimen.corner_radius_grey_background, R.color.grey)

        val radioGroup = dialogLayout.findViewById<RadioGroup>(R.id.radio_group_languages)
        for (i in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(i) as? RadioButton
            radioButton?.buttonTintList = colorStateList
        }

        val continueButton = dialogLayout.findViewById<AppCompatButton>(R.id.btn_continue)
        continueButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedRadioButton = dialogLayout.findViewById<RadioButton>(selectedId)
                val selectedLanguage = selectedRadioButton?.text.toString()
                if (selectedLanguage == context.getString(R.string.english)) {
                    viewModel.saveLanguage("en")
                } else {
                    viewModel.saveLanguage("id")
                }
                viewModel.getLanguage().observeForever {
                    if(selectedLanguage == Locale.getDefault().language) {
                        updateLocale(Locale.getDefault().language.toString())
                        bottomChangeLanguageDialog.dismiss()
                    } else {
                        updateLocale(it)
                        bottomChangeLanguageDialog.dismiss()
                        restartApplication()
                    }
                }
            }
        }

        bottomChangeLanguageDialog.apply {
            setContentView(dialogLayout)
            setCancelable(true)
        }
    }

    private fun updateLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)

        @Suppress("DEPRECATION")
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun restartApplication() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            context.finishAffinity()
        }
    }

    private fun setupShape(cardView: MaterialCardView, cornerSizeResId: Int, colorResId: Int) {
        val cornerSize = context.resources.getDimension(cornerSizeResId)
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setTopLeftCornerSize(cornerSize)
            .setTopRightCorner(RoundedCornerTreatment())
            .setTopRightCornerSize(cornerSize)
            .build()

        cardView.setCardBackgroundColor(ContextCompat.getColor(context, colorResId))
        cardView.shapeAppearanceModel = shapeAppearanceModel
        cardView.strokeWidth = 0
    }

    fun showDialog() {
        bottomChangeLanguageDialog.show()
    }
}