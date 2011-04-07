<#if classMeta.getPackageName()??>
package ${classMeta.getPackageName()};
</#if>

public class ${classMeta.getName()} {
<#list classMeta.getFieldMetas() as f>
	private ${f.getTypeName()} ${f.getName()};
	public void set${f.getName()?cap_first}(${f.getTypeName()} ${f.getName()}){
		this.${f.getName()} = ${f.getName()};
	}
<#if f.getTypeName() == "boolean">
<#assign getter = "is"/>
<#elseif f.getTypeName() == "java.lang.Boolean">
<#assign getter = "is"/>
<#else>
<#assign getter = "get"/>
</#if>  	    
 	public ${f.getTypeName()} ${getter}${f.getName()?cap_first}(){
		return ${f.getName()};
	}
</#list>
}