package kau.msproject.searchaed.ui

data class PushDTO (
    var notification : Notification = Notification(),
    var to : String? = null
){
    data class Notification(var body: String? = null,var title : String? = null)

}