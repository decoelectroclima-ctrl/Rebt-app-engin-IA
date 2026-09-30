
import re

file_path = 'app/src/main/java/com/example/MainViewModel.kt'
with open(file_path, 'r') as f:
    content = f.read()

# Replace all Content.QUESTIONS references
new_content = content.replace('Content.QUESTIONS', 'allQuestions')

# Fix the variable shadowing in generateOfficialSimulationQuestions
# Replace: val allQuestions = ...values.flatMap ...
# With: val allQuestionsList = ...values.flatMap ...
# And update subsequent usages in that function
new_content = re.sub(r'val allQuestions = allQuestions\.values\.flatMap \{ it\.questions \}\.distinctBy \{ it\.q \}', 
                     'val allQuestionsList = allQuestions.values.flatMap { it.questions }.distinctBy { it.q }', 
                     new_content)

# Update references in that function
# This is a bit risky with regex, better to be specific if possible
# ...

with open(file_path, 'w') as f:
    f.write(new_content)
