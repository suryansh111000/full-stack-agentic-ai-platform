from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
import torch
from transformers import AutoTokenizer, AutoModelForCausalLM

# -------------------------------
# Model Setup
# -------------------------------
MODEL_ID = "LiquidAI/LFM2.5-1.2B-Instruct"

tokenizer = AutoTokenizer.from_pretrained(MODEL_ID, trust_remote_code=True)
model = AutoModelForCausalLM.from_pretrained(MODEL_ID, trust_remote_code=True)

device = "cuda" if torch.cuda.is_available() else "cpu"
model.to(device)

print(f"✅ Model loaded on {device}")

# -------------------------------
# App Init
# -------------------------------
app = FastAPI()

# -------------------------------
# DTOs
# -------------------------------
class Message(BaseModel):
    role: str
    content: str

class GenerateRequest(BaseModel):
    messages: List[Message]

# -------------------------------
# Helper: Convert messages properly
# -------------------------------
def format_messages(messages):
    return [{"role": m.role, "content": m.content} for m in messages]

# -------------------------------
# Endpoint
# -------------------------------
@app.post("/generate")
def generate(req: GenerateRequest):

    messages = format_messages(req.messages)

    inputs = tokenizer.apply_chat_template(
        messages,
        add_generation_prompt=True,
        tokenize=True,
        return_dict=True,
        return_tensors="pt",
    ).to(device)

    outputs = model.generate(
        **inputs,
        max_new_tokens=400,
        do_sample=False,
    )

    generated_text = tokenizer.decode(
        outputs[0][inputs["input_ids"].shape[-1]:],
        skip_special_tokens=True
    ).strip()

    return {
        "content": generated_text
    }