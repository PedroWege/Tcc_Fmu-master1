package com.example.wanderson.projeto.Activity
//package br.com.thiengo.introapitests

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.MessageButtonBehaviour
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.View
import com.example.wanderson.projeto.Manifest
import com.example.wanderson.projeto.R

/**
 * Created by Pedro Wege on 23/11/2017.
 */
class IntroActivity : MaterialIntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verifyIntroActivity()

/*          addSlide( SlideFragmentBuilder()
                .backgroundColor( R.color.slide_1 )
                .buttonsColor( R.color.slide_button )
                //.image( R.drawable.slide_1 )
                .title( resources.getString(R.string.slide_1_title) )
                .description( resources.getString(R.string.slide_1_description) )
                .build(),
                MessageButtonBehaviour( object : View.OnClickListener {
                    override fun onClick(view: View?) {
                        showMessage( resources.getString(R.string.slide_1_button_message) )
                    }
                }, resources.getString(R.string.slide_1_button_label)
            )
    )*/
        addSlide(TermsConditionsSlide() )


            backButtonTranslationWrapper
                    .setEnterTranslation {
                        view, percentage -> view.alpha = percentage * 5
                    }
            enableLastSlideAlphaExitTransition( true )


        }
    private fun verifyIntroActivity(){

        if( SPInfo(this).isIntroShown() ){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
