package com.example.mydishes.view.fragments

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
import com.example.mydishes.view.activities.MainActivity
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
    fun dishDetails(favDish : FavDish){

        findNavController().navigate(AllDishesFragmentDirections.actionNavigationAllDishesToDishDetailsFragment(
            favDish
        ))
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    override fun onResume() {
        super.onResume()

        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}