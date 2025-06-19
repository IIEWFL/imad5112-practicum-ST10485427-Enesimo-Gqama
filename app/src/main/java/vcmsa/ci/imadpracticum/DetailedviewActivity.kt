package vcmsa.ci.imadpracticum

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class DetailedviewActivity : AppCompatActivity() {

    private lateinit var song: ArrayList<String>
    private lateinit var artist: ArrayList<String>
    private lateinit var rating: ArrayList<Int>
    private lateinit var comments: ArrayList<String>
    private lateinit var txtDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.detailedview)

        song = intent.getStringArrayListExtra("song title") ?: arrayListOf()
        artist = intent.getStringArrayListExtra("artist name") ?: arrayListOf()
        rating = intent.getIntegerArrayListExtra("rating") ?: arrayListOf()
        comments = intent.getStringArrayListExtra("comments") ?: arrayListOf()
        txtDisplay = findViewById(R.id.txtDisplay)

        val btnDisplayAll: Button = findViewById(R.id.btnDisplayAll)
        val btnCalculate: Button = findViewById(R.id.btnCalculate)
        val btnReturn: Button = findViewById(R.id.btnReutrn)

        btnDisplayAll.setOnClickListener {
            displayPlaylist()
        }
        btnCalculate.setOnClickListener {
            displaySongswithAverage()
        }
        btnReturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayPlaylist() {
        if (song.isEmpty()) {
            txtDisplay.text = "Display list is empty."
            return
        }

        val stringBuilder = StringBuilder()
        for (i in song.indices) {
            stringBuilder.append("song: ${song[i]}\n")
            stringBuilder.append("artist: ${artist[i]}\n")
            stringBuilder.append("rating: ${rating[i]}/5\n")
            stringBuilder.append("comments: ${comments[i]}\n\n")
        }
        txtDisplay.text = stringBuilder.toString()
    }

    private fun displaySongswithAverage() {
        val stringBuilder = StringBuilder()
        var found = false
        for (i in song.indices) {
            if (rating[i] >= 1) {
                stringBuilder.append("song: ${song[i]} (rating: ${rating[i]})\n")
                found = true
            }
        }
        txtDisplay.text = if (found) stringBuilder.toString() else "No songs in playlist."
    }
}