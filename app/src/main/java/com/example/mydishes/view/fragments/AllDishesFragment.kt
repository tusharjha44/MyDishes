package com.example.mydishes.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mydishes.R
import com.example.mydishes.application.FavDishApplication
import com.example.mydishes.databinding.FragmentAllDishBinding
import com.example.mydishes.model.entities.FavDish
import com.example.mydishes.view.activities.AddUpdateDishActivity
import com.example.mydishes.view.adapters.FavDishAdapter
import com.example.mydishes.viewmodel.FavDishViewModel
import com.example.mydishes.viewmodel.FavDishViewModelFactory

class AllDishesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    private var _binding: FragmentAllDishBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllDishBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_dishes,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_dishes -> {
                startActivity(Intent(requireActivity(),AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.rvDishesList?.layoutManager = GridLayoutManager(requireActivity(),2)
        val favDishAdapter = FavDishAdapter(this)
        _binding?.rvDishesList?.adapter = favDishAdapter

        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner){
            dishes ->
            dishes.let {
                if (it.isNotEmpty()) {

                    _binding?.rvDishesList?.visibility = View.VISIBLE
                    _binding?.tvNoDishesAddedYet?.visibility = View.GONE

                    favDishAdapter.dishesList(it)
                } else {

                    _binding?.rvDishesList?.visibility = View.GONE
                    _binding?.tvNoDishesAddedYet?.visibility = View.VISIBLE
                }
            }
        }
    }

    fun deleteDish(dish: FavDish) {
        val builder = AlertDialog.Builder(requireActivity())
        //set title for alert dialog
        builder.setTitle(resources.getString(R.string.title_delete_dish))
        //set message for alert dialog
        builder.setMessage(resources.getString(R.string.msg_delete_dish_dialog, dish.title))
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton(resources.getString(R.string.lbl_yes)) { dialogInterface, _ ->
            mFavDishViewModel.delete(dish)
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton(resources.getString(R.string.lbl_no)) { dialogInterface, _ ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }


    fun dishDetails(favDish : FavDish){

        findNavController().navigate(AllDishesFragmentDirections.actionNavigationAllDishesToDishDetailsFragment(
            favDish
        ))
//        if(requireActivity() is MainActivity){
//            (activity as MainActivity?)!!.hideBottomNavigationView()
//        }
    }

//    override fun onResume() {
//        super.onResume()
//        if(requireActivity() is MainActivity){
//            (activity as MainActivity?)!!.showBottomNavigationView()
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}