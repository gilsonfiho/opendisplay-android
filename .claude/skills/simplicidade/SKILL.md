---
name: simplicidade
description: Permanent code-style rule for this project — always produce the simplest possible solution before writing any new code or editing existing code. Use always, on every development task in this repository, not only when explicitly asked.
---

# Simplicity above all

A project rule, always on — not an optional review, it's how the code should be born.

## Before writing any code

Stop and ask: **is there a simpler solution that meets the same need?** Only go for the more
complex alternative if the answer is clearly no.

## Priorities, in this order

1. **Readability** — code that reads like direct technical prose.
2. **Maintainability** — easy to change later, without having to understand extra layers first.
3. **Clarity** — names that say what the thing is/does, with no comment needed to decode them.

## Avoid by default

- Generic abstractions for a single use case (an interface/base class for a single
  implementer, generics where a concrete type does the job).
- Design patterns (Factory, Strategy, hand-rolled Observer, etc.) when a function or a simple
  class already does the job.
- Premature optimization — only optimize when there is a measured, real performance problem.
- "Just in case" complexity — don't build for hypothetical future requirements.

## How to apply

- Clear, direct names (variable, function, class) — if the name already explains it, don't comment.
- Short methods, one responsibility per function.
- Follow the pattern that already exists in the project (see the README and the neighboring code)
  instead of introducing a new style for the same thing.
- Only use a more complex solution when the simpler one really doesn't work — and, in that case,
  make clear (in a short comment) *why* the simple one wasn't enough.

## Example of application in this project

`net/PhoneReceiver.kt` already follows this: the clock offset calculation (`OffsetSample`) and
the performance metric (`PerfStats`) use plain lists + small private functions, not a generic
"collector" class or a pluggable metrics system — there's no need for that here, it's a single
receiver talking to a single peer.
