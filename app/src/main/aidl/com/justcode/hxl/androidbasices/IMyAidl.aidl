// IMyAidl.aidl
package com.justcode.hxl.androidbasices;
import com.justcode.hxl.androidbasices.process.Person;

// Declare any non-default types here with import statements

interface IMyAidl {
  /**
     * 非基本类型的数据需要导入，比如上面的 Person，需要导入它的全路径。
     * 这里的 Person 我理解的是 Person.aidl，然后通过 Person.aidl 又找到真正的实体 Person 类。
     *方法参数中，除了基本数据类型，其他类型的参数都需要标上方向类型
     * in(输入), out(输出), inout(输入输出)
     */
    void addPerson(in Person person);

    List<Person> getPersonList();

}
