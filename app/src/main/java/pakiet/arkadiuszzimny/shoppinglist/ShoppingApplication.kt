package pakiet.arkadiuszzimny.shoppinglist

import android.app.Application

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import pakiet.arkadiuszzimny.shoppinglist.data.db.ShoppingDatabase
import pakiet.arkadiuszzimny.shoppinglist.data.repositories.ShoppingRepository
import pakiet.arkadiuszzimny.shoppinglist.ui.shoppinglist.ShoppingViewModelFactory

class ShoppingApplication: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ShoppingApplication))
        //Kiedy używamy instance jako parametr, Kodein szuka jakiegoś obiektu, który pasuje.
        // A, że Kodein zadeklarowaliśmy jako lazy dopasowywuje on context aplikacji
        bind() from singleton { ShoppingDatabase(instance()) }
        //Jako intance zostanie podstawiona powyższa Database
        bind() from singleton { ShoppingRepository(instance())}
        //Tym razem nie singleton ponieważ chcemy użyć tej instancji w ShoppingActivity
        bind() from provider { ShoppingViewModelFactory(instance()) }
    }
}