package com.example.retrafit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.retrafit.Adapter.MyRetAdapter
import com.example.retrafit.Retrofit.MyApiClient
import com.example.retrafit.databinding.ActivityMainBinding
import com.example.retrafit.databinding.ItemDialogBinding
import com.example.retrafit.models.MyTodo
import com.example.retrafit.models.PostTodoRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() , MyRetAdapter.RvAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var myRetAdapter: MyRetAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MyApiClient.apicl.getAllTodo()
            .enqueue(/* callback = */ object : Callback<List<MyTodo>>{
                override fun onResponse(
                    call: Call<List<MyTodo>>,
                    response: Response<List<MyTodo>>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body() as ArrayList
                        myRetAdapter = MyRetAdapter(this@MainActivity ,list)
                        binding.rv.adapter = myRetAdapter
                        binding.progresRet.visibility = View.GONE

                    }
                }

                override fun onFailure(call: Call<List<MyTodo>>, t: Throwable) {
                    Toast.makeText(this@MainActivity , "Error ${t.message}" , Toast.LENGTH_LONG).show()
                }
            })

        binding.btnFloatingAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            itemDialogBinding.btnSave.setOnClickListener {
                val postTodoRequest =PostTodoRequest(
                    itemDialogBinding.edtOhirgiMuddat.text.toString(),
                            itemDialogBinding.edtSarlavha.text.toString(),
                            itemDialogBinding.edtHolat.text.toString(),
                            itemDialogBinding.edtMatn.text.toString()
                )
                MyApiClient.apicl.addTodo(postTodoRequest)
                    .enqueue(object : Callback<MyTodo>{
                        override fun onResponse(call: Call<MyTodo>, response: Response<MyTodo>) {
                            Toast.makeText(this@MainActivity, "${response.body()?.sarlavha} saqlandi", Toast.LENGTH_SHORT).show()

                        }

                        override fun onFailure(call: Call<MyTodo>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "Error ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                dialog.cancel()
            }
            dialog.setView(itemDialogBinding.root)
            dialog.show()



        }
    }

    override fun itemClicked(imageView: ImageView, myTodo: MyTodo) {
        val popupmenu = PopupMenu(this,imageView)
        popupmenu.inflate(R.menu.my_menu)

        popupmenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_delete->{
                    MyApiClient.apicl.deleteTodo(myTodo.id)
                        .enqueue(object  :Callback<Any>{
                            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                                Toast.makeText(this@MainActivity, "O'Chirildi", Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<Any>, t: Throwable) {
                                Toast.makeText(this@MainActivity, "Error ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
                R.id.menu_edit->{
                    val dialog = AlertDialog.Builder(this).create()
                    val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)


                    itemDialogBinding.edtSarlavha.setText(myTodo.sarlavha)
                    itemDialogBinding.edtMatn.setText(myTodo.matn)
                    itemDialogBinding.edtOhirgiMuddat.setText(myTodo.oxirgi_muddat)
                    itemDialogBinding.edtHolat.setText(myTodo.holat)


                    itemDialogBinding.btnSave.setOnClickListener {
                        val postTodoRequest = PostTodoRequest(
                            itemDialogBinding.edtOhirgiMuddat.text.toString(),
                            itemDialogBinding.edtSarlavha.text.toString(),
                            itemDialogBinding.edtHolat.text.toString(),
                            itemDialogBinding.edtMatn.text.toString()
                        )
                        MyApiClient.apicl.editTodo(myTodo.id , postTodoRequest)
                            .enqueue(object : Callback<MyTodo>{
                                override fun onResponse(call: Call<MyTodo>, response: Response<MyTodo>) {
                                   if (response.isSuccessful){
                                       Toast.makeText(this@MainActivity, "${response.body()?.sarlavha} O'zgartirildi", Toast.LENGTH_SHORT).show()


                                   }
                                    dialog.cancel()

                                }

                                override fun onFailure(call: Call<MyTodo>, t: Throwable) {
                                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                    }

                    dialog.setView(itemDialogBinding.root)
                    dialog.show()
                }

            }
            true
        }

        popupmenu.show()
    }
}