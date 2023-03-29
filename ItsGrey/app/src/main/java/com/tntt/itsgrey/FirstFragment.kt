package com.tntt.itsgrey

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tntt.home.usecase.HomeUseCase
import com.tntt.model.BookType
import com.tntt.model.SortType

import dagger.hilt.android.AndroidEntryPoint
import itsgrey.app.R
import itsgrey.app.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var homeUseCase: HomeUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            println("button click!")
            val bookIdList = mutableListOf<String>()
            bookIdList.add("78895127-aeca-4273-866f-302c31130adc")
            bookIdList.add("6bac0d13-fe34-48f0-982a-8ce5ff21ec4d")
            HomeUseCaseDeleteBook(bookIdList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun HomeUseCaseCreateBook(userId: String){
        CoroutineScope(Dispatchers.Main).launch {
            homeUseCase.createBook(userId).collect() { createBook ->
                println("createBook = ${createBook}")
            }
        }
    }

    fun HomeUseCaseGetBooks(userId: String, sortType: SortType, startIndex: Long, bookType: BookType) {
        CoroutineScope(Dispatchers.Main).launch {
            homeUseCase.getBooks(userId, sortType, startIndex, bookType).collect() { bookList ->
                for (book in bookList) {
                    Log.d("Tag===================================","book = ${book}")
                }
            }
        }
    }

    fun HomeUseCaseDeleteBook(bookIdList: List<String>) {
        CoroutineScope(Dispatchers.Main).launch {
            homeUseCase.deleteBook(bookIdList).collect() { result ->
                println("HomeUseCaseDeleteBook = ${result}")
            }
        }
    }
}