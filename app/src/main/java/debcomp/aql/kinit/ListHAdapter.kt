package debcomp.aql.kinit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_hero.view.*

/*
 *
 * @author Davi Aquila
 *
 */

class ListHAdapter(private val context: Context, private var heroesList: MutableList<Hero>) : RecyclerView.Adapter<ListHAdapter.ListHHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem_hero, parent, false)
        return (ListHHolder(view))
    }

    override fun getItemCount(): Int = heroesList.size

    override fun onBindViewHolder(holder: ListHHolder, position: Int) {
        holder.bindView(heroesList[position])
    }

    inner class ListHHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hero_name = itemView.hero_name
        var hero_rate = itemView.hero_rate

        fun bindView(hero: Hero) {
            hero_name.text = hero.name
            hero_rate.text = hero.rating.toString()

        }
    }
}
