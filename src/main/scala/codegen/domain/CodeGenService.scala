package codegen.domain

import freemarker.template.Configuration
import java.io.{FileWriter, File}
import codegen.utils.using
import scala.collection.JavaConverters._

class CodeGenService(classMetas: List[ClassMeta], exportDir: File, templateDir: File) {

  def generate = {
    val configuration = new Configuration
    configuration.setDirectoryForTemplateLoading(templateDir)
    val template = configuration.getTemplate("java.ftl")
    classMetas.foreach {
      classMeta =>
        val rootMap = Map("classMeta" -> classMeta)
        val exportClassDir = getExportClassDir(classMeta)
        exportClassDir.mkdirs
        using(new FileWriter(new File(exportClassDir, classMeta.name + ".java"))) {
          fileWriter =>
            template.process(rootMap.asJava, fileWriter);
            fileWriter.flush();
        }
    }
  }

  protected def getExportClassDir(classMeta: ClassMeta) = classMeta.packageName match {
    case Some(packageName) => {
      val packageDir = packageName.replaceAll("\\.", "/")
      new File(exportDir, packageDir)
    }
    case None => exportDir
  }


}