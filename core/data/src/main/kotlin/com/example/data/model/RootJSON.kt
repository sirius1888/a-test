package com.example.data.model

public data class RootJSON(
    val TestMode: Boolean,
    val EmulationMode: Boolean,
    val ProfileElements: List<ProfileElement>
)

public data class ProfileElement(
    val Column: Column? = null,
    val Row: Row? = null,
    val Button: Button? = null,
    val LastName: LastName? = null,
    val Gender: Gender? = null,
    val Birthday: Birthday? = null
)

public data class Column(
    val Id: String,
    val Hide: Boolean,
    val ProfileElements: List<ProfileElement>
)

public data class Row(
    val Id: String,
    val Hide: Boolean,
    val ProfileElements: List<ProfileElement>
)

public data class Button(
    val Id: String,
    val Label: String,
    val Hide: Boolean,
    val Disabled: Boolean,
    val Action: Action?
)

public data class LastName(
    val Id: String,
    val Label: String,
    val Hide: Boolean,
    val Disabled: Boolean,
    val IgnoreCustomerData: Boolean,
    val Validators: List<Validator>,
    val Field: String? = null
)

public data class Gender(
    val Id: String,
    val Label: String,
    val Hide: Boolean,
    val Disabled: Boolean,
    val IgnoreCustomerData: Boolean,
    val SupportValues: List<String>,
    val GenderValueMaps: List<GenderValueMap>,
    val Field: String? = null
)

public data class Birthday(
    val Id: String,
    val Label: String,
    val Hide: Boolean,
    val Disabled: Boolean,
    val IgnoreCustomerData: Boolean,
    val Validators: List<Validator>,
    val Field: String? = null
)

public data class Action(
    val HideElementAction: HideElementAction? = null,
    val ShowElementAction: ShowElementAction? = null,
    val DisableElementAction: DisableElementAction? = null,
    val EnableElementAction: EnableElementAction? = null,
    val ReloadDataAction: ReloadDataAction? = null,
    val SaveCustomerAction: SaveCustomerAction? = null,
    val ActionGroup: ActionGroup? = null
)

public data class ActionGroup(
    val Actions: List<Action>
)

public data class HideElementAction(val ProfileElementId: String)
public data class ShowElementAction(val ProfileElementId: String)
public data class DisableElementAction(val ProfileElementId: String)
public data class EnableElementAction(val ProfileElementId: String)
public object ReloadDataAction
public data class SaveCustomerAction(
    val LastNameId: String,
    val GenderId: String,
    val BirthdayId: String
)

public object Validator
public data class GenderValueMap(
    val GenderType: String,
    val Label: String
)