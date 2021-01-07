package pakiet.arkadiuszzimny.shoppinglist.ui.shoppinglist

import pakiet.arkadiuszzimny.shoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItem)
}