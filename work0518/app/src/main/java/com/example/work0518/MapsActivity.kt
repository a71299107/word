package com.example.work0518

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.work0518.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val REQUET_PERMISSIONS = 1

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isEmpty()) return
        when(requestCode){
            REQUET_PERMISSIONS -> {
                for(result in grantResults)
                    if(result != PackageManager.PERMISSION_GRANTED)
                        finish()
                    else{
                        //Step1：連接MapFragment元件
                        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        //Step2：執行map的非同步方法
                        map.getMapAsync(this)
                    }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        //檢查使用者是否已授權定位權限
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED)
        //檢查使用者是否已授權定位權限
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUET_PERMISSIONS)
        else{
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            val map = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            map.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        //使用者是否已授權定位權限
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return
        //顯示目前位置與目前位置的按鈕
        map.isMyLocationEnabled = true
        //建立MarkerOptions物件
        val marker = MarkerOptions()
        marker.position(LatLng(25.033611, 121.565000))
        marker.title("台北101")
        marker.draggable(true)
        map.addMarker(marker)
        marker.position(LatLng(25.047924, 121.517081))
        marker.title("台北車站")
        marker.draggable(true)
        map.addMarker(marker)
        //加入PolylineOptions到googleMap
        val polylineOpt = PolylineOptions()
        polylineOpt.add(LatLng(25.033611, 121.565000))
        polylineOpt.add(LatLng(25.032728, 121.565137))
        polylineOpt.add(LatLng(25.047924, 121.565081))
        polylineOpt.color(Color.BLUE)
        val polyline = map.addPolyline(polylineOpt)
        polyline.width = 10f
        //移動鏡頭
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.034,121.545), 13f))

    }
}