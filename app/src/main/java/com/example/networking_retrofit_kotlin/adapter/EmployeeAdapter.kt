package com.example.networking_retrofit_kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.networking_retrofit_kotlin.R
import com.example.networking_retrofit_kotlin.model.Employee
import com.example.networking_retrofit_kotlin.utils.Alerts

class EmployeeAdapter(var context: Context,
                      var items: ArrayList<Employee>,
                      val apiDeletePoster:(Employee) -> Unit,
                      val apiUpdatePoster: (Employee, Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PosterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_poster_list, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PosterViewHolder){
            holder.bind(position)
        }
    }

    override fun getItemCount() = items.size

    inner class PosterViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var tv_name: TextView = view.findViewById(R.id.tv_name)
        private var tv_age: TextView = view.findViewById(R.id.tv_age)
        private var tv_salary: TextView = view.findViewById(R.id.tv_salary)

        private var ll_update: LinearLayout = view.findViewById(R.id.ll_update)
        private var ll_delete: LinearLayout = view.findViewById(R.id.ll_delete)

        fun bind(position: Int){
            var poster = items[position]

            tv_name.text = poster.employee_name
            tv_age.text = poster.employee_age.toString()
            tv_salary.text = poster.employee_salary.toString()

            ll_update.setOnClickListener {
                Alerts.createrDialog(context, poster){
                    apiUpdatePoster.invoke(it, position)
                }
            }

            ll_delete.setOnClickListener {
                Alerts.deleterDialog(context) {
                    apiDeletePoster.invoke(poster)
                }
            }
        }
    }
}