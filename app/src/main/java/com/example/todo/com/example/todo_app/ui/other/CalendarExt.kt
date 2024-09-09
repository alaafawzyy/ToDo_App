package fragment

import java.util.Calendar

public fun Calendar.clearTime(){
     set(Calendar.MINUTE,0)
    set(Calendar.MILLISECOND,0)
    set(Calendar.SECOND,0)
    set(Calendar.HOUR_OF_DAY,0)
}