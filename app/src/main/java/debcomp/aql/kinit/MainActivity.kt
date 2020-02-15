package debcomp.aql.kinit

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var etxtName:  EditText
    lateinit var ratinBar:  RatingBar
    lateinit var btnSave:   Button
    lateinit var fab:       FloatingActionButton

    lateinit var heroList: MutableList<Hero>

    val db = FirebaseDatabase.getInstance()
    var ref: DatabaseReference = db.getReference("heroes")
    lateinit var data: DataSnapshot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etxtName = findViewById(R.id.etxtName)
        ratinBar = findViewById(R.id.ratingBar)
        btnSave = findViewById(R.id.btnSave)
        fab = findViewById(R.id.fab)

        btnSave.setOnClickListener(this)
        fab.setOnClickListener(this)


        heroList = mutableListOf()
        charge()

        rv_listHeroes.layoutManager = LinearLayoutManager(this)
        rv_listHeroes.adapter = ListHAdapter(this,heroList)
    }

    override fun onStart() {
        super.onStart()
        //val account = GoogleSignIn.getLastSignedInAccount(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {
                R.id.btnSave -> {
                    saveHero()
                }
                R.id.fab -> {
                    charge()
                }

             }
        }


    }

    private fun charge() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                data = p0;
                if(p0!!.exists()) {
                    heroList.clear()
                    p0.children.forEach { h ->
                        val hero = h.getValue(Hero::class.java)
                        heroList.add(hero!!)
                    }
                }
            }


        })
    }

    private fun saveHero() {
        val name = etxtName.text.toString().trim()

        if (name.isEmpty()) {
            etxtName.error = "Please enter a name"
            return
        }
        val ref = db.getReference("heroes")
        val heroId = ref.push().key.toString()
        val hero = Hero(heroId, name, ratinBar.rating.toInt())
        var x = ref.ref.child(heroId).setValue(hero).addOnCompleteListener {
            Toast.makeText(applicationContext, "Hero saved", Toast.LENGTH_LONG).show()
        }
    }

}

