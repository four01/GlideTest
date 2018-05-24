package net.four01.glidetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val html = """<img src="https://upload.wikimedia.org/wikipedia/commons/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg">
            """

        val view = findViewById<TextView>(R.id.body)

        val imageGetter = ContentImageGetter(this)

        val spans = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT, imageGetter, null) as (Spannable)

        view.text = spans
    }
}
