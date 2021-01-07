package pakiet.arkadiuszzimny.shoppinglist.ui.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_shopping.*
import pakiet.arkadiuszzimny.shoppinglist.R
import pakiet.arkadiuszzimny.shoppinglist.data.db.ShoppingDatabase
import pakiet.arkadiuszzimny.shoppinglist.data.db.entities.ShoppingItem
import pakiet.arkadiuszzimny.shoppinglist.data.repositories.ShoppingRepository
import pakiet.arkadiuszzimny.shoppinglist.other.ShoppingItemAdapter

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val database = ShoppingDatabase(this)
        val repository = ShoppingRepository(database)
        val factory = ShoppingViewModelFactory(repository)

        val viewModel = ViewModelProviders.of(this).get(ShoppingViewModel::class.java)

        val adapter = ShoppingItemAdapter(listOf(), viewModel)

        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter

        //używamy metody observe ponieważ metoda zadeklarowana w interfejsie Dao zwraca obiekt LiveData
        viewModel.getAllShopingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
            //aktualizujemy dane adaptera (nawet jesli ktoś usunie tylko jeden element)
        })

        fab.setOnClickListener{
            AddShoppingItemDialog(this,
            object: AddDialogListener{
                override fun onAddButtonClicked(item: ShoppingItem) {
                    viewModel.upsert(item)
                }
            }).show()
        }
    }
}