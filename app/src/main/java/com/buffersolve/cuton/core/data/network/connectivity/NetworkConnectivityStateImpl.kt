package com.buffersolve.cuton.core.data.network.connectivity

import android.content.Context
import android.net.*
import com.buffersolve.cuton.core.domain.NetworkConnectivityState
import com.buffersolve.cuton.core.domain.State
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkConnectivityStateImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkConnectivityState {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override suspend fun requestNetworkStatus(): Flow<State> {

        val networkStatus = callbackFlow<State> {

            val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onUnavailable() {
                    trySend(State.Unavailable).isSuccess
                }

                override fun onAvailable(network: Network) {
                    trySend(State.Available).isSuccess
                }

                override fun onLost(network: Network) {
                    trySend(State.Lost).isSuccess
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    trySend(State.Losing).isSuccess
                }

//                override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
//                    trySend(State.Unavailable).isSuccess
//                }
            }

            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            connectivityManager.registerNetworkCallback(request, networkStatusCallback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(networkStatusCallback)
            }
        }

        return networkStatus
    }
}