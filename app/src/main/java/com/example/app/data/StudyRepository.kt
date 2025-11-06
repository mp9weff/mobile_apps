package com.example.app.data

import kotlinx.coroutines.flow.Flow

class StudyRepository(
    private val subjectDao: SubjectDao,
    private val labWorkDao: LabWorkDao
) {
    fun observeSubjects(): Flow<List<Subject>> = subjectDao.observeAll()
    fun observeSubject(subjectId: Long): Flow<Subject?> = subjectDao.observeById(subjectId)
    fun observeLabs(subjectId: Long): Flow<List<LabWork>> = labWorkDao.observeBySubject(subjectId)
    fun observeLab(labId: Long): Flow<LabWork?> = labWorkDao.observeById(labId)

    suspend fun addSubject(name: String, teacher: String? = null): Long =
        subjectDao.insert(Subject(name = name, teacher = teacher))

    suspend fun updateSubject(subject: Subject) = subjectDao.update(subject)
    suspend fun deleteSubject(subject: Subject) = subjectDao.delete(subject)

    suspend fun addLab(subjectId: Long, title: String): Long =
        labWorkDao.insert(LabWork(subjectId = subjectId, title = title))

    suspend fun updateLab(lab: LabWork) = labWorkDao.update(lab)
    suspend fun deleteLab(lab: LabWork) = labWorkDao.delete(lab)
}


