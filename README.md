# scratchpad-MultiAutoCompleteTextView

## Original source of example code

The code used in this example is based on the example found at
https://codinginflow.com/tutorials/android/multiautocompletetextview

## Bug in MultiAutoCompleteTextView#performValidation()

How to trigger:

- Run the activity.
- Select one or more countries from the drop-down.
- Trigger Validation by tabbing to the button.
- Wait for the Application to ANR.

## Bodge fix

- Subclass MultiAutoCompleteTextView
  - Capture the Tokenizer as it is private once passed in.
  - Override performValidation() and alter the penultimate line of the
  loop. Modifying the code "i = start;" to "i = start - 2;" works in
  this case with the CommaTokenizer but I don't know if it will work in
  general.
- To test the bodge, alter the layout code line at the start of
MainActivity.onCreate() to use 'R.layout.activity_main_bodge'.

