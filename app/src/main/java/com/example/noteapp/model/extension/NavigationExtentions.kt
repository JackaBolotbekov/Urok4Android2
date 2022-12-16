package com.example.noteapp.model.extension
//
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//
//fun <J : Any> Fragment.setBackStackData(key: String, data: J, doBack: Boolean) {
//    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
//    if (doBack) {
//        findNavController().navigateUp()
//    }
//}
//
//fun <J : Any> Fragment.getBackStackData(key: String, result: (J) -> (Unit)) {
//    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<J>(key)
//        ?.observe(viewLifecycleOwner) {
//        result(it)
//    }
//}