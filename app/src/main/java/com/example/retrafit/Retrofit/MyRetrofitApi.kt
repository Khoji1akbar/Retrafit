package com.example.retrafit.Retrofit

import com.example.retrafit.models.MyTodo
import com.example.retrafit.models.PostTodoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MyRetrofitApi {

    @GET("plan")
    fun getAllTodo(): Call<List<MyTodo>>

    @POST("plan/")
    fun addTodo(@Body postTodoRequest: PostTodoRequest):Call<MyTodo>


    @DELETE("plan/{id}/")
    fun deleteTodo(@Path("id") id:Int):Call<Any>

    @PUT("plan/{id}/")
    fun editTodo(@Path("id") id: Int ,@Body postTodoRequest: PostTodoRequest):Call<MyTodo>
}