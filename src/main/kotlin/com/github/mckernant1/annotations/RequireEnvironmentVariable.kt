package com.github.mckernant1.annotations

import com.github.mckernant1.extensions.collections.except
import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


@Target(AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireEnvironmentVariable(vararg val envStrings: String)


@AutoService(Processor::class)
@SupportedAnnotationTypes("com.github.mckernant1.annotations.RequireEnv")
class RequireEnvProcessor : AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(RequireEnvironmentVariable::class.java).forEach {
            val envsToCheck = it.getAnnotation(RequireEnvironmentVariable::class.java).envStrings.toList()
            val envsNotPresent = envsToCheck.except(System.getenv().keys)
            processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "$envsNotPresent are not present in environment")
            if (envsNotPresent.isNotEmpty()) error("$envsNotPresent are not present")
        }
        return true
    }

}