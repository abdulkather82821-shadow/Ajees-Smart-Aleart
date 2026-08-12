package com.ajees.shadowalert

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

private data class MonitoredApp(val name: String, val used: Int, val limit: Int)
class MainActivity : ComponentActivity() { override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { ShadowAlertApp() } } }

@Composable fun ShadowAlertApp(vm: ShadowAlertViewModel = viewModel()) {
    MaterialTheme { var tab by remember { mutableIntStateOf(0) }; Scaffold(bottomBar={NavigationBar{listOf("Home","Apps","Analytics","Settings").forEachIndexed{i,label->NavigationBarItem(selected=tab==i,onClick={tab=i},icon={},label={Text(label)})}}}){pad->when(tab){0->HomeScreen(vm,Modifier.padding(pad));1->AppsScreen(vm,Modifier.padding(pad));2->AnalyticsScreen(Modifier.padding(pad));else->SettingsScreen(vm,Modifier.padding(pad))}} }
}
@Composable fun HomeScreen(vm:ShadowAlertViewModel,modifier:Modifier=Modifier){Column(modifier.fillMaxSize().padding(20.dp)){Text("AS SHADOW ALERT",style=MaterialTheme.typography.headlineMedium);Text("Use with intention. Live with balance.");Spacer(Modifier.height(20.dp));Card(Modifier.fillMaxWidth()){Column(Modifier.padding(18.dp)){Text("Screen Time Today");Text("3h 24m",style=MaterialTheme.typography.displaySmall);Text("Keep your time intentional.")}};Spacer(Modifier.height(18.dp));Text("Monitored Applications",style=MaterialTheme.typography.titleLarge);vm.apps.forEach{AppCard(it)}}}
@Composable fun AppCard(app:MonitoredApp){Card(Modifier.fillMaxWidth().padding(vertical=6.dp)){Column(Modifier.padding(16.dp)){Text(app.name,style=MaterialTheme.typography.titleMedium);Text("${app.used}m / ${app.limit}m");LinearProgressIndicator(progress={(app.used.toFloat()/app.limit).coerceIn(0f,1f)},Modifier.fillMaxWidth())}}}
@Composable fun AppsScreen(vm:ShadowAlertViewModel,modifier:Modifier=Modifier){Column(modifier.fillMaxSize().padding(20.dp)){Text("Manage Apps",style=MaterialTheme.typography.headlineSmall);Spacer(Modifier.height(12.dp));OutlinedTextField(vm.search,{vm.search=it},Modifier.fillMaxWidth(),label={Text("Search applications")});Spacer(Modifier.height(12.dp));LazyColumn{items(vm.apps.filter{it.name.contains(vm.search,true)}){AppCard(it)}}}}
@Composable fun AnalyticsScreen(modifier:Modifier=Modifier){Column(modifier.fillMaxSize().padding(20.dp)){Text("Weekly Analytics",style=MaterialTheme.typography.headlineSmall);Spacer(Modifier.height(20.dp));Text("This Week: 18h 40m");Text("Last Week: 22h 10m");Text("↓ 15.8%",style=MaterialTheme.typography.headlineMedium);Text("Great! Your screen time decreased this week.");Spacer(Modifier.height(24.dp));Text("Limits completed: 8");Text("Blocked sessions: 3")}}
@Composable fun SettingsScreen(vm:ShadowAlertViewModel,modifier:Modifier=Modifier){Column(modifier.fillMaxSize().padding(20.dp)){Text("Settings",style=MaterialTheme.typography.headlineSmall);Spacer(Modifier.height(16.dp));Button(onClick=vm::openUsageAccess){Text("Grant Usage Access")};Button(onClick=vm::openOverlay){Text("Grant Overlay Permission")};Text("Privacy: usage data stays on this device.")}}

class ShadowAlertViewModel(app:android.app.Application):AndroidViewModel(app){
    var search by mutableStateOf("")
    val apps=listOf(MonitoredApp("Instagram",28,30),MonitoredApp("YouTube",42,60),MonitoredApp("Games",55,60),MonitoredApp("WhatsApp",20,45))
    fun openUsageAccess(){getApplication<android.app.Application>().startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))}
    fun openOverlay(){getApplication<android.app.Application>().startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).setData(android.net.Uri.parse("package:${getApplication<android.app.Application>().packageName}")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))}
}
