package com.example.networking_retrofit_kotlin.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.networking_retrofit_kotlin.R
import com.example.networking_retrofit_kotlin.model.Employee

class Alerts {
    companion object {

        fun createrDialog(context: Context, employee: Employee?, createEmployee: (Employee) -> Unit) {

            val alertDialog = AlertDialog.Builder(context)

            val view = LayoutInflater.from(context).inflate(R.layout.alert_create_dialog, null)
            val edt_age = view.findViewById<EditText>(R.id.edt_create_age)
            val edt_name = view.findViewById<EditText>(R.id.edt_create_name)
            val edt_salary = view.findViewById<EditText>(R.id.edt_create_salary)

            alertDialog.setView(view)

            if (employee != null){
                edt_age.text.insert(0, employee.employee_age.toString())
                edt_name.text.insert(0, employee.employee_name.toString())
                edt_salary.text.insert(0, employee.employee_salary.toString())

                alertDialog.setTitle("Update Employee")
            } else {
                alertDialog.setTitle("Create Employee")
            }

            alertDialog.setNegativeButton("Cancel") { dialog, which ->
                dialog!!.dismiss()
                Log.d("RRR", "-> cancel")
            }
            alertDialog.setPositiveButton("Save") { dialog, which ->

                if (edt_age.text.isNotEmpty() && edt_name.text.isNotEmpty() && edt_salary.text.isNotEmpty()) {

                    val employee_age = edt_age.text.toString()
                    val employee_name = edt_name.text.toString()
                    val employee_salary = edt_salary.text.toString()

                    val emp = Employee(employee_age.toInt(), employee_name, employee_salary.toInt(), employee?.id ?: 1, "")

                    createEmployee.invoke(emp)

                } else {
                    Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialog.create().show()

        }

        fun deleterDialog(context: Context, deleteEmployee: () -> Unit){
            androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Delete Employee")
                .setMessage("Are you sure you want to delete this employee?")
                .setPositiveButton(android.R.string.yes) { dialog, which ->
                    deleteEmployee.invoke()
                }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create()
                .show()
        }
    }
}