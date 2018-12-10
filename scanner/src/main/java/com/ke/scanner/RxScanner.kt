package com.ke.scanner

import android.support.v4.app.FragmentActivity
import io.reactivex.Observable

class RxScanner(activity: FragmentActivity) {

    private val tag = "rxScanner"

    private val delegateFragment: DelegateFragment

    init {
        val fragment = activity.supportFragmentManager.findFragmentByTag(tag)

        if (fragment == null) {
            delegateFragment = DelegateFragment()
            activity.supportFragmentManager.beginTransaction().add(delegateFragment, tag).commitNow()
        } else {
            delegateFragment = fragment as DelegateFragment
        }
    }


    fun startScan(): Observable<ScanResult> {

        delegateFragment.start()
        return Observable.just(1)
            .flatMap {
                delegateFragment.scanResultSubject
            }
    }
}