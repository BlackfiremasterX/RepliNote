package com.deepdweller.replinote.helpers

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.deepdweller.replinote.R
import com.deepdweller.replinote.databinding.AddLabelBinding
import com.deepdweller.replinote.databinding.DialogInputBinding
import com.deepdweller.replinote.room.Label

interface OperationsParent {

    fun accessContext(): Context

    fun insertLabel(label: Label, onComplete: (success: Boolean) -> Unit)

    fun labelNote(labels: List<String>, currentLabels: HashSet<String>, onUpdated: (labels: HashSet<String>) -> Unit) {
        val checkedPositions = BooleanArray(labels.size) { index ->
            val label = labels[index]
            currentLabels.contains(label)
        }

        val inflater = LayoutInflater.from(accessContext())
        val addLabel = AddLabelBinding.inflate(inflater).root

        val builder = MaterialAlertDialogBuilder(accessContext())
            .setTitle(R.string.labels)
            .setNegativeButton(R.string.cancel, null)

        if (labels.isNotEmpty()) {
            builder.setMultiChoiceItems(labels.toTypedArray(), checkedPositions) { dialog, which, isChecked ->
                checkedPositions[which] = isChecked
            }
            builder.setPositiveButton(R.string.save) { dialog, which ->
                val selectedLabels = HashSet<String>()
                checkedPositions.forEachIndexed { index, checked ->
                    if (checked) {
                        val label = labels[index]
                        selectedLabels.add(label)
                    }
                }
                onUpdated(selectedLabels)
            }
        } else builder.setView(addLabel)

        val dialog = builder.create()

        addLabel.setOnClickListener {
            dialog.dismiss()
            displayAddLabelDialog(currentLabels, onUpdated)
        }

        dialog.show()
    }

    private fun displayAddLabelDialog(currentLabels: HashSet<String>, onUpdated: (labels: HashSet<String>) -> Unit) {
        val inflater = LayoutInflater.from(accessContext())
        val binding = DialogInputBinding.inflate(inflater)

        MaterialAlertDialogBuilder(accessContext())
            .setTitle(R.string.add_label)
            .setView(binding.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.save) { dialog, which ->
                val value = binding.EditText.text.toString().trim()
                if (value.isEmpty()) {
                    dialog.dismiss()
                } else {
                    val label = Label(value)
                    insertLabel(label) { success ->
                        if (success) {
                            dialog.dismiss()
                            labelNote(listOf(value), currentLabels, onUpdated)
                        } else binding.root.error = accessContext().getString(R.string.label_exists)
                    }
                }
            }
            .show()

        binding.EditText.requestFocus()
    }
}