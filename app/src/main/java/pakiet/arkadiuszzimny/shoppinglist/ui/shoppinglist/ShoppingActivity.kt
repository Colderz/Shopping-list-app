package pakiet.arkadiuszzimny.shoppinglist.ui.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_shopping.*
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import pakiet.arkadiuszzimny.shoppinglist.R
import pakiet.arkadiuszzimny.shoppinglist.data.db.ShoppingDatabase
import pakiet.arkadiuszzimny.shoppinglist.data.db.entities.ShoppingItem
import pakiet.arkadiuszzimny.shoppinglist.data.repositories.ShoppingRepository
import pakiet.arkadiuszzimny.shoppinglist.other.ShoppingItemAdapter

class ShoppingActivity : AppCompatActivity(), KodeinAware {

    //stosowny import
    override val kodein by kodein()

    private val factory: ShoppingViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val viewModel = ViewModelProviders.of(this, factory).get(ShoppingViewModel::class.java)

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