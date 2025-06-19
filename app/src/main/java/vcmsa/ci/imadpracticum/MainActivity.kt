package vcmsa.ci.imadpracticum

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val song = mutableListOf<String>()
    private val artist = mutableListOf<String>()
    private val rating = mutableListOf<String>()
    private val comments = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnAddToPlayList = findViewById<Button>(R.id.btnAddToPlayList)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnExit = findViewById<Button>(R.id.btnExit)

        btnAddToPlayList.setOnClickListener {
            showAddSongDialog()
        }

        btnNext.setOnClickListener {
            if (song.isNotEmpty()) {
                val intent = Intent(this, DetailedviewActivity::class.java)
                intent.putStringArrayListExtra("song", ArrayList(song))
                intent.putStringArrayListExtra("artist", ArrayList(artist))
                intent.putStringArrayListExtra("rating", ArrayList(rating))
                intent.putStringArrayListExtra("comments", ArrayList(comments))
                startActivity(intent)
            } else {
                Snackbar.make(btnNext, "Detailed view is empty. Add songs first.", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnExit.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }

    private fun showAddSongDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add new song")

        val view = layoutInflater.inflate(R.layout.dialogadditem, null)
        val edtSongTitle: EditText = view.findViewById(R.id.edtSongTitle)
        val edtArtistName: EditText = view.findViewById(R.id.edtArtistName)
        val edtRatings: EditText = view.findViewById(R.id.edtRatings)
        val edtSongComment: EditText = view.findViewById(R.id.edtComment)

        builder.setView(view)

        builder.setPositiveButton("Add") { dialog, _ ->
            val songTitle = edtSongTitle.text.toString().trim()
            val artistName = edtArtistName.text.toString().trim()
            val ratingStr = edtRatings.text.toString().trim()
            val commentText = edtSongComment.text.toString().trim()

            if (songTitle.isEmpty() || artistName.isEmpty() || ratingStr.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Song title, artist name, and rating cannot be empty.", Snackbar.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val ratingValue = ratingStr.toIntOrNull()
            if (ratingValue == null || ratingValue <= 0) {
                Snackbar.make(findViewById(android.R.id.content), "Invalid rating. Please enter a number greater than zero.", Snackbar.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            song.add(songTitle)
            artist.add(artistName)
            rating.add(ratingStr)
            comments.add(commentText)

            Snackbar.make(findViewById(android.R.id.content), "\"$songTitle\" added to the playlist.", Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}