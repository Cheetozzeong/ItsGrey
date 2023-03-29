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
import com.tntt.domain.drawing.usecase.DrawingUseCase
import com.tntt.editbook.model.Book
import com.tntt.editbook.usecase.EditBookUseCase
import com.tntt.editpage.model.Page
import com.tntt.editpage.usecase.EditPageUseCase
import com.tntt.home.usecase.HomeUseCase
import com.tntt.model.*
import com.tntt.viewer.usecase.ViewerUseCase

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
    @Inject
    lateinit var editBookUseCase: EditBookUseCase
    @Inject
    lateinit var editPageUseCase: EditPageUseCase
    @Inject
    lateinit var drawingUseCase: DrawingUseCase
    @Inject
    lateinit var viewerUsecase: ViewerUseCase

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

            val bookId = "26e4f471-6380-440e-ac28-8f3dcdfc0e0d"
            val pageInfo = PageInfo("pageInfoId12", 2)

            val boxData = BoxData(0.2f, 0.2f, 0.6f, 0.6f)
            val upBoxData = BoxData(0.2f, 0.2f, 0.6f, 0.3f)
            val downBoxData = BoxData(0.2f, 0.55f, 0.6f, 0.3f)

            CoroutineScope(Dispatchers.Main).launch {
                drawingUseCase.retrofitTest().collect() { result ->
                    Log.d("function test", "retrofitTest = ${result}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun HomeUseCaseCreateBook(userId: String, bookId: String){
        /*
        실행 코드
            val bookIdList = mutableListOf<String>()
            bookIdList.add("78895127-aeca-4273-866f-302c31130adc")
            bookIdList.add("6bac0d13-fe34-48f0-982a-8ce5ff21ec4d")
         */
        CoroutineScope(Dispatchers.Main).launch {
            homeUseCase.createBook(userId, bookId).collect() { createBook ->
                println("createBook = ${createBook}")
            }
        }
    }

    fun HomeUseCaseGetBooks(userId: String, sortType: SortType, startIndex: Long, bookType: BookType) {
        CoroutineScope(Dispatchers.Main).launch {
            homeUseCase.getBooks(userId, sortType, startIndex, bookType).collect() { bookList ->
                for (book in bookList) {
                    Log.d("function test","book = ${book}")
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

    fun EditBookUseCaseCreatePage(bookId: String, pageInfo: PageInfo, imageBoxInfo: ImageBoxInfo?, textBoxInfo: TextBoxInfo?){
        /*
        실행 코드
            val bookId = "1f843ade-3c2f-4a7c-a873-58678a057ed0"
            val pageInfo = PageInfo("pageInfoId3", 2)
            val boxData = BoxData(0.2f, 0.2f, 0.6f, 0.6f)
            val upBoxData = BoxData(0.2f, 0.2f, 0.6f, 0.3f)
            val downBoxData = BoxData(0.2f, 0.55f, 0.6f, 0.3f)

            EditBookUseCaseCreatePage(bookId, pageInfo, null, TextBoxInfo("textBoxId3", "이것도 만들어져라!", 1.0f, boxData))
         */
        CoroutineScope(Dispatchers.Main).launch {
            editBookUseCase.createPage(bookId, pageInfo, imageBoxInfo, textBoxInfo).collect() { page ->
                println("EditBookUseCaseCreatePage = ${page}")
            }
        }
    }

    fun EditBookUseCaseGetBook(bookId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            editBookUseCase.getBook(bookId).collect() { book ->
                Log.d("function test", "EditBookUseCaseGetBook(${bookId}) = ${book}")
            }
        }
    }

    fun EditBookUseCaseSaveBook(book: Book, userId: String, bookType: BookType = BookType.EDIT) {
        CoroutineScope(Dispatchers.Main).launch {
            editBookUseCase.saveBook(book, userId, bookType).collect() { result ->
                Log.d("function test", "editBookUseCase.saveBook : ${result}")
            }
        }
    }

    fun EditBookUseCasePublishBook(book: Book, userId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            editBookUseCase.publishBook(book, userId).collect() { result ->
                Log.d("function test", "editBookUseCase.publishBook : ${result}")
            }
        }
    }

    fun EditPageUseCaseCreateImageBox(pageId: String, imageBoxInfo: ImageBoxInfo) {
        CoroutineScope(Dispatchers.Main).launch {
            editPageUseCase.createImageBox(pageId, imageBoxInfo).collect() { imageBoxInfoResult ->
                Log.d("function test", "createImageBox = ${imageBoxInfoResult}")
            }
        }
    }

    fun EditPageUseCaseCreateTextBox(pageId: String, textBoxInfo: TextBoxInfo) {
        CoroutineScope(Dispatchers.Main).launch {
            editPageUseCase.createTextBox(pageId, textBoxInfo).collect() { textBoxInfoResult ->
                Log.d("function test", "createTextBox = ${textBoxInfoResult}")
            }
        }
    }

    fun EditPageUseCaseGetPage(pageId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            editPageUseCase.getPage(pageId).collect() { page ->
                Log.d("function test", "getPage = ${page}")
            }
        }
    }

    fun EditPageUseCaseSavePage(page: Page) {
        CoroutineScope(Dispatchers.Main).launch {
            editPageUseCase.savePage(page).collect() { result ->
                Log.d("function test", "savePage = ${result}")
            }
        }
    }

    fun EditPageUseCaseDeleteImageBox(imageBoxId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            editPageUseCase.deleteImageBox(imageBoxId).collect() { result ->
                Log.d("function test", "deleteImageBox = ${result}")
            }
        }
    }

    fun EditPageUseCaseDeleteTextBox(textBoxId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            editPageUseCase.deleteTextBox(textBoxId).collect() { result ->
                Log.d("function test", "deleteTextBox = ${result}")
            }
        }
    }

    fun ViewerUseCaseGetBook(bookId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            viewerUsecase.getBook(bookId).collect() { book ->
                Log.d("function test", "getBook = ${book}")
            }
        }
    }
}