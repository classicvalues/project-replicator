/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.android.gradle.replicator.generator.project

import com.android.gradle.replicator.generator.BuildGenerator
import com.android.gradle.replicator.generator.manifest.ManifestGenerator
import com.android.gradle.replicator.generator.util.WildcardString
import com.android.gradle.replicator.generator.writer.DslWriter
import com.android.gradle.replicator.generator.writer.GroovyDslWriter
import com.android.gradle.replicator.generator.writer.KtsWriter
import com.android.gradle.replicator.model.DependenciesInfo
import com.android.gradle.replicator.model.ModuleInfo
import com.android.gradle.replicator.model.ProjectInfo
import java.io.File

interface ProjectGenerator {

    fun generateRootModule(project: ProjectInfo)

    fun generateModule(
        folder: File,
        module: ModuleInfo
    )

    fun generateSettingsFile(project: ProjectInfo)

    fun close()

    companion object {
        fun createGenerator(
            params: BuildGenerator.Params,
            libraryFilter: Map<WildcardString, String>,
            libraryAdditions: Map<WildcardString, List<DependenciesInfo>>
        ): ProjectGenerator {
            val resGenerator = ManifestGenerator()

            val dslWriter: DslWriter = if (params.kts) KtsWriter(true) else GroovyDslWriter(true)

            return GradleProjectGenerator(
                params.destination,
                libraryFilter,
                libraryAdditions,
                dslWriter,
                resGenerator
            )
        }
    }
}